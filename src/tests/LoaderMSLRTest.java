package tests;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import util.LoaderMSLR;

public class LoaderMSLRTest {

	@Test
	public void getDataSetTest() throws FileNotFoundException{
	
	String path = System.getProperty("user.dir").concat(
			File.separator+"datasets"+File.separator+"MSLR-WEB10K"+
					File.separator+"Fold1"+File.separator+"sampleTrain.txt");
	
	LoaderMSLR.getDataset(path);
	
	
	}
}