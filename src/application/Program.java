package application;

import model.entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre o caminho do arquivo: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Sale> sales = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] vect = line.split(",");
                if (vect.length == 5) {
                    try {
                        sales.add(new Sale(
                                Integer.parseInt(vect[0]),
                                Integer.parseInt(vect[1]),
                                vect[2],
                                Integer.parseInt(vect[3]),
                                Double.parseDouble(vect[4])
                        ));
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao processar os dados da linha: " + line);
                    }
                } else {
                    System.out.println("Linha inválida: " + line);
                }

                line = br.readLine();
            }

            // Ordenar pela média de preço
            sales.sort(Comparator.comparing(Sale::averagePrice).reversed());

            System.out.print("Veja as cinco maiores vendas no ano (DIGITE UM ANO): ");
            int highestSalesOfYear = sc.nextInt();

            // Filtrar vendas do ano especificado e obter as 5 maiores
            List<Sale> highestSales = sales.stream()
                    .filter(s -> s.getYear().equals(highestSalesOfYear))
                    .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            // Exibir as vendas
            System.out.println("Cinco maiores vendas no ano " + highestSalesOfYear + ":");
            for (Sale sale : highestSales) {
                System.out.println(sale);
            }

            System.out.print("Veja como foram os meses 1 e 7 no ano escolhido do funcionario (DIGITE UM NOME): ");
            sc.nextLine(); // Consumir a quebra de linha deixada pelo nextInt()
            String employee = sc.nextLine();

            // Calcular o total de vendas nos meses 1 e 7 para o funcionário
            Double sum = sales.stream()
                    .filter(v -> v.getSeller().equals(employee) && (v.getMonth() == 1 || v.getMonth() == 7))
                    .mapToDouble(Sale::getTotal)
                    .sum();

            System.out.println("Total de vendas nos meses 1 e 7 para " + employee + ": " + String.format("%.2f", sum));

        } catch (IOException e) {
            System.out.println("Erro: "+path+" (O sistema não pode encontrar o arquivo especificado) " + e.getMessage());
        }

        sc.close();
    }
}
