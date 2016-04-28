package com.ilyon.tools.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ilyon.tools.annotation.Category;
import com.ilyon.tools.annotation.Feature;
import com.ilyon.tools.service.SummaryService;
import com.ilyon.tools.vo.CategoryVo;
import com.ilyon.tools.vo.FeatureVo;

@Service
public class SummaryServiceImpl implements SummaryService {
	private final List<CategoryVo> categories=new ArrayList<CategoryVo>();
	private final Map<String,FeatureVo> featuresByUrl=new HashMap<String,FeatureVo>();
	private static final Logger LOGGER=Logger.getLogger(SummaryServiceImpl.class);
	@PostConstruct
	public void postSummary() {
		try {
			buildByPackage("com.ilyon.tools.controller");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildByPackage(String packgetName) throws IOException {
		ClassLoader classLoader = SummaryServiceImpl.class.getClassLoader();
		String fileResource = packgetName.replace('.', '/');
		LOGGER.debug(fileResource);
		Enumeration<URL> resources = classLoader.getResources(fileResource);
		if (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			File file = new File(url.getFile());
			for (String f : file.list()) {
				String[] split = f.split("\\.");
				if (split.length < 2)
					continue;
				if (split[1].equals("class")) {
					try {
						Class<?> loadClass = classLoader.loadClass(packgetName
								+ "." + split[0]);
						Category beanAn =loadClass.getAnnotation(Category.class);
						Controller controller=loadClass.getAnnotation(Controller.class);;
						if(beanAn!=null&&controller!=null){
							CategoryVo categoryVo=new CategoryVo();
							categories.add(categoryVo);
							categoryVo.setDesc(beanAn.desc());
							categoryVo.setName(beanAn.name());
							Method[] methods = loadClass.getMethods();
							for(Method method:methods){
								Feature feature = method.getAnnotation(Feature.class);
								RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
								if(Member.DECLARED==method.getModifiers()&&feature!=null&&requestMapping!=null){
									FeatureVo featureVo=new FeatureVo();
									featureVo.setDesc(feature.desc());
									featureVo.setName(feature.name());
									featureVo.setUrl(feature.url());
									featureVo.setSourceClass(loadClass.getName());
									categoryVo.getFeatures().add(featureVo);
									featuresByUrl.put(feature.url(), featureVo);
								}
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			throw new IllegalArgumentException(packgetName + " can't be found!");
		}
	}

	@Override
	public List<CategoryVo> listCategories() {
		return categories;
		
	}

	@Override
	public Set<String> listCodeFiles(String url) {
		FeatureVo featureVo = featuresByUrl.get(url);
		String sourceClass = featureVo.getSourceClass();
		ClassLoader classLoader = SummaryServiceImpl.class.getClassLoader();
		Set<String> importClasses=new HashSet<String>();
		findImports(sourceClass, classLoader, importClasses);
		return importClasses;
	}

	private void findImports(String sourceClass, ClassLoader classLoader,
			Set<String> importClasses) {
		if(importClasses.contains(sourceClass)){
			return;
		}
		importClasses.add(sourceClass);
		InputStream stream = classLoader.getResourceAsStream(sourceClass.replace(".","/")+".java");
		InputStreamReader ir=new InputStreamReader(stream);
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        Set<String> tmpImportClasses=new HashSet<String>();
        try {
        	boolean importFinish=true;
			while ((line = input.readLine ()) != null) {
				line=line.trim();
				if(line.startsWith("package")||line.contains("*")||line.isEmpty()){
					continue;
				}
				if(line.startsWith("import")){
					importFinish=false;
					if(line.contains("com.ilyon.tools")){
						tmpImportClasses.add(line.replaceFirst("import","").replace(";", "").trim());
					}
				}else{
					importFinish=true;
				}
				if(importFinish){
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        importClasses.addAll(tmpImportClasses);
        for(String cl:tmpImportClasses){
        	try {
				Class<?> forName = Class.forName(cl);
				if(forName.isInterface()&&!forName.isAnnotation()){
					String implPkg=forName.getPackage().getName();
					String fileResource = implPkg.replace('.', '/');
					findImpls(classLoader, importClasses, cl, fileResource);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	findImports(cl,classLoader,importClasses);
        }
        
	}

	@Override
	public String getClassContent(String clsName) {
		ClassLoader classLoader = SummaryServiceImpl.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(clsName.replace(".","/")+".java");
		InputStreamReader ir=new InputStreamReader(stream);
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        StringBuffer buf=new StringBuffer();
        try {
			while ((line = input.readLine ()) != null) {
				buf.append(line).append("\n");
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
		return buf.toString();
	}

	private void findImpls(ClassLoader classLoader,
			Set<String> tmpImportClasses, String cl, String fileResource)
			throws IOException, ClassNotFoundException {
		Enumeration<URL> resources = classLoader.getResources(fileResource);
		if (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			File file=new File(url.getFile());
			if(file.isDirectory()){
				File[] listFiles = file.listFiles();
				for(File one:listFiles){
					if(one.isFile()&&one.getName().endsWith(".class")){
						Class<?> tempClass = Class.forName(fileResource.replace("/", ".")+"."+one.getName().replace(".class", ""));
						if(tmpImportClasses.contains(tempClass.getName())){
							continue;
						}
						Class<?>[] interfaces = tempClass.getInterfaces();
						boolean isImpl=false;
						for(Class<?> inter:interfaces){
							if(inter.getName().equals(cl)){
								isImpl=true;
							}
						}
						if(isImpl){
							findImports(tempClass.getName(),classLoader,tmpImportClasses);
						}
					}else if(one.isDirectory()){
						findImpls(classLoader, tmpImportClasses, cl, fileResource+"/"+one.getName());
					}
				}
			}
		}
	}
}
