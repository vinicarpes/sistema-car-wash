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
        Scanner scanner = new Scanner(System.in);

        Cliente pessoaFisica = criarPessoaFisica(scanner, formatter);
        System.out.println(pessoaFisica.getDados("Cliente no Serasa"));

//        Cliente pessoaJuridica = criarPessoaJuridica(scanner, formatter);
//        System.out.println(pessoaJuridica.getDados());

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

        LocalDate dataCadastro = obterData("Data de cadastro (dd/MM/yyyy): ", scanner, formatter);

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

        LocalDate dataCadastro = obterData("Data de cadastro (dd/MM/yyyy): ", scanner, formatter);
        LocalDate nascimento = obterData("Nascimento (dd/MM/yyyy): ", scanner, formatter);

        return new PessoaFisica(id, nome, telefone, email, dataCadastro, cpf, nascimento);
    }

    public static LocalDate obterData(String mensagem, Scanner scanner, DateTimeFormatter formatter) {
        LocalDate data = null;
        while (data == null) {
            try {
                System.out.println(mensagem);
                data = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Tente novamente.");
            }
        }
        return data;
    }
}
