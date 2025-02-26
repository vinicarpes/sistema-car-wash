/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/MainApp.Main.java to edit this template
 */
package MainApp;

import domain.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author User
 **/


public class MainApp {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        Scanner scanner = new Scanner(System.in);

        Cor cor1 = new Cor("Vermelho");
        Marca marca1 = new Marca("VW");
        Modelo modelo1 = new Modelo("Gol 1.0", marca1);
        Veiculo veiculo = new Veiculo(1,"4654fwsf",modelo1, "OBS", cor1);
        Cliente pessoaFisica = new PessoaFisica(1, "Vinicius", "48984830045", "vinicius@gmail.com", today, "12345678977", today);

        pessoaFisica.add(veiculo);
//        pessoaFisica.imprimirVeiculos();

        Servico limpezaLuxo = new Servico(1, "Limpeza de luxo completa", 500, ECategoria.GRANDE);
        Servico limpezaBasica = new Servico(2, "Limpeza básica", 500, ECategoria.PEQUENO);

        OrdemServico os1 = new OrdemServico(15462, 500, today);
        os1.setDesconto(100);
        os1.setVeiculo(veiculo);

        ItemOS item1 = new ItemOS();
        item1.setOrdemServico(os1);
        item1.setServico(limpezaLuxo);
        item1.setValorServico(480);

        ItemOS item2 = new ItemOS();
        item2.setOrdemServico(os1);
        item2.setServico(limpezaBasica);
        item2.setValorServico(100);

        os1.add(item1);
        os1.add(item2);
        os1.setDesconto(19);

        System.out.println(ImpressaoOS.imprimirOS(os1));


        scanner.close();
    }

    public static PessoaJuridica criarPessoaJuridica(Scanner scanner, DateTimeFormatter formatter) {
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpa o buffer
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("CNPJ: ");
        String cnpj = scanner.nextLine();
        System.out.println("Inscrição Estadual: ");
        String inscricaoEstadual = scanner.nextLine();
        System.out.println("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();

        LocalDate dataCadastro = obterData(scanner, formatter);

        return new PessoaJuridica(id, nome, telefone, email, dataCadastro, cnpj, inscricaoEstadual);

    }

    public static PessoaFisica criarPessoaFisica(Scanner scanner, DateTimeFormatter formatter) {
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpa o buffer
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("CPF: ");
        String cpf = scanner.nextLine();
        System.out.println("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();

        LocalDate dataCadastro = obterData(scanner, formatter);
        LocalDate nascimento = obterData( scanner, formatter);

        return new PessoaFisica(id, nome, telefone, email, dataCadastro, cpf, nascimento);
    }

    public static LocalDate obterData(Scanner scanner, DateTimeFormatter formatter) {
        LocalDate data = null;
        while (data == null) {
            try {
                data = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Tente novamente.");
            }
        }
        return data;
    }
}
