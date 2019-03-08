package com.jnntml;

import com.jnngl.plugins.tools.HTML;

public class Test {

	public static void test(String[] args) {
		HTML html = new HTML();
		
		System.out.println(html.HTMLize("<!DOCTYPE HTML> <html> <body> <p><b>Hello</b> <color=Color.01>World!</color></p> </body> </html>"));
	}

}
