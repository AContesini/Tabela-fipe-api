package br.com.tabela.fipe.principal;

import br.com.tabela.fipe.dadosGerais.AnoEspecificacoes;
import br.com.tabela.fipe.dadosGerais.Dados;
import br.com.tabela.fipe.dadosGerais.Modelo;
import br.com.tabela.fipe.service.Fipe;
import br.com.tabela.fipe.service.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_ = "https://parallelum.com.br/fipe/api/v1/";
    private Service consumo = new Service();
    private Fipe converte = new Fipe();

    public void ExibirMenu(){

        String menu = """
                Digite a tipo de veiculo para busca.
                Carro
                Moto
                Caminhão
                """;
        System.out.println(menu);

        var opcao = leitura.nextLine();
        String endereco;
        if(opcao.toUpperCase().contains("CAR")){
            endereco = URL_+"carros/marcas";
        } else if (opcao.toUpperCase().contains("MOT")) {
            endereco = URL_+"motos/marcas";

        }else {
            endereco = URL_+"caminhoes/marcas";
        }

        String json= consumo.ObterDado(endereco);
        System.out.println(json);

        var marca = converte.ObterdadosFipeLista(json,Dados.class);

        marca.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o código da Marca que você busca");
        var codModelo = leitura.nextLine();

        endereco = endereco + "/"+codModelo+"/modelos";


        json = consumo.ObterDado(endereco);

        var modelo = converte.ObterdadosFipe(json, Modelo.class);

        modelo.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome modelo que você procura");
        var modeloFiltro = leitura.nextLine();

        List<Dados> modelosFiltrados = modelo.modelos().stream()
                .filter(m -> m.nome().toUpperCase().contains(modeloFiltro.toUpperCase()))
                .collect(Collectors.toList());

        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nEscolha códido do modelo para mais detalhes ");

        codModelo = leitura.nextLine();
        endereco = endereco+"/"+codModelo+"/anos";
        json = consumo.ObterDado(endereco);

        List<Dados> ano = converte.ObterdadosFipeLista(json,Dados.class);
        List<AnoEspecificacoes> especificacoes = new ArrayList<>();

        for (int i = 0; i < ano.size(); i++) {
           var enderecoAnos = endereco +"/"+ano.get(i).codigo();
           json = consumo.ObterDado(enderecoAnos);
           AnoEspecificacoes veiculo = converte.ObterdadosFipe(json,AnoEspecificacoes.class);
           especificacoes.add(veiculo);

        }
        System.out.println("\n Lista detalhada ");
        especificacoes.forEach(System.out::println);


    }


}
