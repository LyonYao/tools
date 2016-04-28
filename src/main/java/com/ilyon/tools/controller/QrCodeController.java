package com.ilyon.tools.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ilyon.tools.annotation.Category;
import com.ilyon.tools.annotation.Feature;
import com.ilyon.tools.vo.ProfileVo;

/**
 * @author lyon
 *
 */
@Controller
@RequestMapping(value="rqCode")
@Category(name="二维码工具",desc="")
public class QrCodeController {
	@Feature(name="生成简单二维码",url="rqCode/textRqCode",desc="")
	@RequestMapping("/textRqCode")
	public ModelAndView listCodeFiles(){
		return new ModelAndView("qrcode/textqr");
		
	}
	@RequestMapping("/textRqCodeGenerate")
	public ModelAndView viewClass(@RequestParam("content") String content) throws WriterException, IOException{
		// 图片尺寸
		String encodeAsString = generateQrCode(content);
		return new ModelAndView("qrcode/textqr","img","data:image/jpg;base64,"+encodeAsString);
		
	}
	private String generateQrCode(String content) throws WriterException,
			IOException {
		int size=160;
		Map<EncodeHintType,Object> hint=new HashMap<EncodeHintType,Object> ();
		hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		BitMatrix byteMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size,hint);  
		BufferedImage buf=new BufferedImage(byteMatrix.getWidth(), byteMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graphics = buf.getGraphics();
		graphics.clearRect(0, 0, size, size);
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, size, size);
		graphics.setColor(Color.BLACK);
		for (int i = 0; i < size; i++) {  
            for (int j = 0; j < size; j++) {  
                if (byteMatrix.get(i,j)) {  
                	graphics.fillRect(i, j, 1, 1);
                }else{
                	//buf.setRGB(i, j, 0);
                }
            }  
        }  
		buf.flush();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(buf, "jpg", out);
		Base64  encoder = new Base64();
		String encodeAsString = encoder.encodeAsString(out.toByteArray());
		return encodeAsString;
	}
	@Feature(name="生成二维码名片",url="rqCode/profileRqCode",desc="")
	@RequestMapping("/profileRqCode")
	public ModelAndView qrcodeProfile(){
		return new ModelAndView("qrcode/profile");
	}
	@RequestMapping("/profileRqCodeGenerate")
	public ModelAndView qrcodeProfileGenerate(ProfileVo profile){
		String template="BEGIN:VCARD\nVERSION:3.0\nFN:%s\nTEL;CELL;VOICE:%s\nTEL;WORK;VOICE:%s\nTEL;WORK;FAX:%s\nEMAIL;PREF;INTERNET:%s\n"
				+ "URL:%s\norG:%s\nROLE:%s\nTITLE:%s\nADR;WORK:%s\nEND:VCARD";
		try {
			String encodeAsString = generateQrCode(String.format(template, new Object[]{profile.getName(),profile.getCellphone(),profile.getTellphone(),profile.getFax(),profile.getEmail(),
					profile.getUrl(),profile.getCompany(),profile.getRole(),profile.getTitle(),profile.getAddress()}));
			return new ModelAndView("qrcode/profile","img","data:image/jpg;base64,"+encodeAsString);
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView("qrcode/profile");
	}
}
