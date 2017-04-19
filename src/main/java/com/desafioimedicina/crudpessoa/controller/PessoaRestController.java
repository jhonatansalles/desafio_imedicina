package com.desafioimedicina.crudpessoa.controller;
 
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafioimedicina.crudpessoa.model.Pessoa;
import com.desafioimedicina.crudpessoa.service.PessoaService;
 
@RestController
public class PessoaRestController {
 
    @Autowired
    PessoaService pessoaService;
    
    /**
     * Realiza importacao da planilha excel
     * @param inputFile : MultipartFile
     * @return ResponseEntity<Void>(headers, HttpStatus)
     */
    @PostMapping("/pessoa/importacao")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile inputFile) {
        if (!inputFile.isEmpty()) {
            try {
                int i = 0;

                // Creates a workbook object from the uploaded excelfile
                XSSFWorkbook workbook = new XSSFWorkbook(inputFile.getInputStream());

                // Creates a worksheet object representing the first sheet
                XSSFSheet worksheet = workbook.getSheetAt(0);

                // Reads the data in excel file until last row is encountered
                while (i <= worksheet.getLastRowNum()) {

                    // Creates an object for the UserInfo Model
                    Pessoa pessoa = new Pessoa();

                    // Creates an object representing a single row in excel
                    XSSFRow row = worksheet.getRow(i++);

                    // Sets the Read data to the model class
                    
                    pessoa.setNome(obtemCelulaComoString(row.getCell(0), new DataFormatter()));
                    pessoa.setDataNascimento(row.getCell(1).getDateCellValue());
                    pessoa.setEmail(obtemCelulaComoString(row.getCell(2), new DataFormatter()));
                    pessoa.setTelefone(obtemCelulaComoString(row.getCell(3), new DataFormatter()));

                    if (!pessoaService.isPessoaExistente(pessoa)) {
                    	pessoaService.incluir(pessoa);
                    } else {
                    	System.out.println("Já existe Um usuário com nome " + pessoa.getNome());
                    }
                }
                workbook.close();

                System.out.println("Arquivo importado com sucesso!");
                return new ResponseEntity<Void>(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        } else {
        	System.out.println("Nenhum arquivo enviado");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
    }
    
    private String obtemCelulaComoString(Cell celula, DataFormatter formatter) {
        return formatter.formatCellValue(celula);
    }
    
    /**
     * Obtem lista com todas as pessoas cadastradas
     * @return ResponseEntity<List<Pessoa>>(HttpStatus)
     */
    @RequestMapping(value = "/pessoa/", method = RequestMethod.GET)
    public ResponseEntity<List<Pessoa>> listarTodasPessoas() {
        List<Pessoa> users = pessoaService.obterTodasPessoas();
        if(users.isEmpty()){
            return new ResponseEntity<List<Pessoa>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Pessoa>>(users, HttpStatus.OK);
    }
 
    /**
     * Obtem Pessoa pelo ID
     * @param id : long
     * @return ResponseEntity<Pessoa>(user, HttpStatus)
     */
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pessoa> getPessoa(@PathVariable("id") long id) {
        System.out.println("Buscando o usuário com id " + id);
        Pessoa user = pessoaService.obter(id);
        
        // valida se encontrou alguma pessoa
        if (user == null) {
            System.out.println("Usuário com id " + id + " não encontrado");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pessoa>(user, HttpStatus.OK);
    }
     
    /**
     * Inclue uma nova Pessoa
     * @param pessoa : Pessoa
     * @param ucBuilder : UriComponentsBuilder
     * @return ResponseEntity<Void>(headers, HttpStatus)
     */
    @RequestMapping(value = "/pessoa/", method = RequestMethod.POST)
    public ResponseEntity<Void> incluirPessoa(@RequestBody Pessoa pessoa,    UriComponentsBuilder ucBuilder) {
        System.out.println("Criando Usuário " + pessoa.getNome());
 
        if (pessoaService.isPessoaExistente(pessoa)) {
            System.out.println("Já existe Um usuário com nome " + pessoa.getNome());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        pessoaService.incluir(pessoa);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/pessoa/{id}").buildAndExpand(pessoa.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
     
    /**
     * Altera uma Pessoa ja cadastrada
     * @param id : long
     * @param pessoa : Pessoa
     * @return ResponseEntity<Pessoa>(pessoaAtual, HttpStatus)
     */
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Pessoa> alterarPessoa(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
        System.out.println("Alterando Usuário " + id);
         
        Pessoa pessoaAtual = pessoaService.obter(id);
         
        if (pessoaAtual==null) {
            System.out.println("Usuário com ID " + id + " não encontrado");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }
        
        pessoaAtual.setNome(pessoa.getNome());
        pessoaAtual.setDataNascimento(pessoa.getDataNascimento());
        pessoaAtual.setEmail(pessoa.getEmail());
        pessoaAtual.setTelefone(pessoa.getTelefone());
         
        pessoaService.alterar(pessoaAtual);
        
        return new ResponseEntity<Pessoa>(pessoaAtual, HttpStatus.OK);
    }
     
    /**
     * Exclui uma Pessoa pelo ID
     * @param id : long
     * @return ResponseEntity<Pessoa>(HttpStatus)
     */
    @RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Pessoa> excluirPessoa(@PathVariable("id") long id) {
        System.out.println("Obtendo e excluindo usuário com id " + id);
 
        Pessoa user = pessoaService.obter(id);
        if (user == null) {
            System.out.println("Não foi possível excluir. Usuário com id " + id + " não encontrado");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }
 
        pessoaService.excluir(id);
        return new ResponseEntity<Pessoa>(HttpStatus.NO_CONTENT);
    }
     
    /**
     * Exluir todas as Pessoas cadastradas
     * @return ResponseEntity<Pessoa>(HttpStatus)
     */
    @RequestMapping(value = "/pessoa/", method = RequestMethod.DELETE)
    public ResponseEntity<Pessoa> excluirTodasPessoas() {
        System.out.println("Excluindo todos os usuários");
 
        pessoaService.excluirTodasPessoas();
        return new ResponseEntity<Pessoa>(HttpStatus.NO_CONTENT);
    }
}