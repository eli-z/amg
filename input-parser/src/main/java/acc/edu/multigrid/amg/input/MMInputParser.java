package acc.edu.multigrid.amg.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acc.edu.multigrid.amg.datatypes.concrete.GraphRepresentation;
import acc.edu.multigrid.amg.datatypes.faces.Datatype;



public class MMInputParser{
	private static Logger logger = LogManager.getLogger(MMInputParser.class);
	public Datatype parseInputFormURI(URI uri) {
		return parseInputFormPath(Paths.get(uri));
	}

	
	public Datatype parseInputFormPath(Path path)
	{
		logger.info("Starting input parsing for file " + path.toFile().getAbsolutePath());
		Datatype result = preparse(path);
		if(result != null)
			try(Stream<String> lines = Files.lines(path)){
				String dataLine = result.getTotalRows() + " " + result.getTotalCols() + " " + result.getTotalValues();
				lines.filter(line -> !line.startsWith("%") && !line.equals(dataLine))
					.parallel()
					.forEach(line -> {
						String[] split = line.trim().split(" ");
						try{
							result.insertValue(Integer.valueOf(split[0]),
									Integer.valueOf(split[1]),
									result.isOnesMatrix() ? null : Double.parseDouble(split[2]));
						}catch(IndexOutOfBoundsException | NumberFormatException e){
							logger.error("Cannot parse input line in input file " + path.toFile().getAbsolutePath() + " line: " + line);
						}
					});
				
			} catch (IOException e) {
				logger.error("Cannot preparse input file " + path.toFile().getAbsolutePath(), e);
				return null;
			}
		return result;
	}
	private Datatype preparse(Path path) {
		Datatype result = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))){
			boolean firstData = true;
			boolean symetric = false, skew = false;
			int rows = 0, cols = 0, values = 0;
			String line;
			String[] valuesS;
			line = reader.readLine();
			if(line != null && line.startsWith("%%MatrixMarket"))
			{
				valuesS = line.replace(" +", " ").trim().split(" ");
				if(valuesS.length >= 3)
				{
					if(!valuesS[2].equalsIgnoreCase("coordinate"))
						logger.error("The given format is not formatted as 'coordinate' matrix");
					if(valuesS.length >= 5)
					{
						switch(valuesS[4])
						{
						case "skew-symmetric": skew = true;
						case "symmetric": symetric = true; break;
						}
					}
				}
				else
					logger.warn("Input MM file does not have matrix description information, assuming non symetric matrix");
			}
			else
				logger.warn("Input MM file does not have matrix description information, assuming non symetric matrix");

			while((line = reader.readLine()) != null)
			{
				if(line.trim().startsWith("%"))//doc line, skip it
					continue;
				valuesS = line.trim().split(" ");
				if(firstData) //firs line, dimentions and values
				{

					if(valuesS.length != 3)
					{
						logger.error("Input MM file does not have matrix data information");
						break;
					}
					try{
						rows = Integer.parseInt(valuesS[0]);
						cols = Integer.parseInt(valuesS[1]);
						values = Integer.parseInt(valuesS[2]);
					}catch(NumberFormatException e){
						logger.error("Corrupted format in file " + path.toFile().getAbsolutePath(), e);
						break;
					}
					firstData = false;
				}
				else
				{
					result = new GraphRepresentation(valuesS.length == 2, rows, cols, values, symetric, skew, true);
					break;
				}
			}

		}catch(IOException e){
			logger.error("Cannot preparse input file " + path.toFile().getAbsolutePath(), e);
			result = null;
		}
		return result;
		
	}
	
	
	public static void main(String[] args) throws URISyntaxException, IOException{
		Files.list(Paths.get("C:\\final-proj\\testMat")).forEach(p -> parse(p.toFile().toURI()));
	}
	
	private static void parse(URI resource){
		long time  = System.currentTimeMillis();
		GraphRepresentation mr = (GraphRepresentation) new MMInputParser().parseInputFormURI(resource);
		logger.info("Took: " + (System.currentTimeMillis() - time));
		logger.info(mr.toString() );
	}
	

}
