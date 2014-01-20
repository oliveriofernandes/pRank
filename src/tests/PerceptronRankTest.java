package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import util.Example;
import util.LoaderMSLR;

import coreCSR.PerceptronRank;

public class PerceptronRankTest {

	@Test
	public void execute() throws FileNotFoundException {

		String path = System.getProperty("user.dir").concat(
				File.separator + "datasets" + File.separator + "MSLR-WEB10K"
						+ File.separator + "Fold1" + File.separator + "sampleTrain.txt");

		List<Example> examples = LoaderMSLR.getDataset(path);
		
		for (Example example : examples) {
			System.out.println("id = " + example.rId);
			System.out.println("qtd of rows = " + example.offerings.numOfRows);
		}

		PerceptronRank pRank = new PerceptronRank(examples.get(0),100);
		
		

	}
}
