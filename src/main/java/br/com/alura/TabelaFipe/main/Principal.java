package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.models.DadosFipe;
import br.com.alura.TabelaFipe.models.Modelos;
import br.com.alura.TabelaFipe.models.Veiculo;
import br.com.alura.TabelaFipe.services.ConsumoApi;
import br.com.alura.TabelaFipe.services.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String  URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        var menu = """
                *** Opções ***
                *   Carro    *
                *   Moto     *
                *   Caminhão *
                **************
                """;

        System.out.println(menu);
        System.out.println("Digite uma opção: ");
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

        var marcas = conversor.obterLista(json, DadosFipe.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosFipe::codigo))
                .forEach(System.out::println);

        System.out.println("\nInforme a marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosFipe::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<DadosFipe> modeloFiltrado = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados: ");
        modeloFiltrado.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo para buscar valores de avaliações: ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(endereco);
        List<DadosFipe> anos = conversor.obterLista(json, DadosFipe.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for(int i = 0; i < anos.size(); i++){
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);

    }
}
