package at.coon_and_friends_2019.ue5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.pmw.tinylog.Logger;

import at.coon_and_friends.foundation.BaseMainClass;
import at.coon_and_friends.foundation.FileUtil;
import at.coon_and_friends.foundation.InputLinesHolder;

public class UE5 extends BaseMainClass {
	
	public static final String INPUT_FILE_ENDING = ".in";
	
	public static final String OUTPUT_FILE_ENDING = ".out";

	public static void main(String[] args) throws Exception {
		String year = "19";
		String level = "5";
		String inputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\in\\";
		String outputDirectory = "C:\\KMA\\Dropbox\\Dropbox\\ccc\\" + year + "\\" + level + "\\out\\";

		for (int nr = 0; nr <= 5; nr++) {
			Logger.warn("---------------------------------------------------");
			Logger.warn("Durchgang " + nr);

			String levelnumber = nr + "";

			String content = FileUtil.readFile(inputDirectory + "level" + level + "_" + levelnumber + INPUT_FILE_ENDING);
			String[] lines = content.split("\n");	
			InputLinesHolder inputLinesHolder = new InputLinesHolder(lines);

			StringBuilder outputStringBuilder = new StringBuilder();

			process(inputLinesHolder, outputStringBuilder);

			Logger.info(outputStringBuilder.toString());
			FileUtil.writeFile(outputDirectory + "level" + level + "_" + levelnumber + OUTPUT_FILE_ENDING,
					outputStringBuilder.toString());
		}
	}

	private static void process(InputLinesHolder inputLinesHolder, StringBuilder outputStringBuilder) {
		int numberOfSolarPanels = inputLinesHolder.getNextLineAsInt();
		List<SolarPanel> panels = new ArrayList<>();
		for(int i = 0;i<numberOfSolarPanels;i++) {
			SolarPanel panel = new SolarPanel(i, inputLinesHolder);
			panels.add(panel);
		}
		
		Plan plan = new Plan(inputLinesHolder);
		
		for(Country country : plan.countries) {
			Logger.info("Country " + country.capital.countryId + ", X: "+ country.capital.x + ", Y: " + country.capital.y);
			Logger.info("Neihgbors " + country.getNeighborCountries());
		}
		
		SimpleWeightedGraph<Cell, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Cell, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for(Country country : plan.countries) {
			graph.addVertex(country.capital);
		}
		
		for(Country country : plan.countries) {
			Set<Long> neihgborCountryIds = country.getNeighborCountries();
			for(Long neighbourCountryId : neihgborCountryIds) {
				Country neighbor = plan.getCountryById(neighbourCountryId);
				double distance = country.capital.calcDistanceToCell(neighbor.capital);
				DefaultWeightedEdge edge = graph.addEdge(country.capital, neighbor.capital);
				if(edge != null) {
					graph.setEdgeWeight(edge, Math.floor(distance));
				}
			}
		}
		
		DijkstraShortestPath<Cell, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<Cell, DefaultWeightedEdge>(graph);
		
		for(Country country : plan.countries) {
			Iterator<SolarPanel> panelIt = panels.iterator();
			while(panelIt.hasNext()) {
				SolarPanel panel = panelIt.next();
				double weight = 0;
				if(panel.countryId != country.countryId) {
					Cell locationOfPanel = plan.getCountryById(panel.countryId).capital;
					GraphPath<Cell,DefaultWeightedEdge> graphPath = dijkstraShortestPath.getPath(country.capital, locationOfPanel);
					weight = graphPath.getWeight();
				}

				outputStringBuilder.append((int)(weight + panel.price));
				if(panelIt.hasNext()) {
					outputStringBuilder.append(" ");
				}
			}
			outputStringBuilder.append("\n");
		}
		
	}
}
