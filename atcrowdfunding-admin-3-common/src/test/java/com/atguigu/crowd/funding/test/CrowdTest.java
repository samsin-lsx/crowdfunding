package com.atguigu.crowd.funding.test;

import org.junit.Test;

import com.atguigu.crowd.funding.util.CrowdFundingUtils;

public class CrowdTest {
	@Test
	public void testMD5() {
		System.out.println(CrowdFundingUtils.md5("123123"));
	}
}
