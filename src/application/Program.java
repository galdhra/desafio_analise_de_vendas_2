package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		System.out.println();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			List<Sale> list = new ArrayList<>();

			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Set<String> names = list.stream().map(x -> x.getSeller()).collect(Collectors.toSet());

			Map<String, Double> nameT = new HashMap<>();

			for (String name : names) {
				double tEach = list.stream().filter((x -> x.getSeller().toUpperCase().matches(name.toUpperCase())))
						.map(x -> x.getTotal()).reduce(0.0, (x, y) -> x + y);

				nameT.put(name, tEach);
			}

			System.out.println("Total de vendas por vendedor:");
			nameT.forEach((x, y) -> System.out.printf("%s - R$ %.2f%n", x, y));

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		sc.close();
	}

}
