package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.services.ConsumoApi;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String  URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();

    public void exibeMenu(){
        var menu = """
                *** Opções ***
                *   Carro    *
                *   Moto     *
                *   Caminhão *
                **************
                """;
        System.out.println();
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if(opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }
        var json = consumoApi.obterDados(endereco);
        System.out.println(json);
    }
}
