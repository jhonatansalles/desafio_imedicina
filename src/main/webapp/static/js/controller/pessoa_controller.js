'use strict';

angular.module('myApp').controller('PessoaController', ['$scope', 'PessoaService', function($scope, PessoaService) {
    var self = this;
    self.pessoa={id:null, nome:'', dataNascimento:'', email:'', telefone:''};
    self.listaPessoas=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
    self.uploadFile = uploadFile;
    self.successMessage = '';
    self.errorMessage = '';

    obterTodasPessoas();
    
    function uploadFile() {
        var file = $scope.myFile;
        PessoaService.uploadFileToUrl(file)
        	.then(
			obterTodasPessoas,
        	function (response) {
				$scope.myFile = null;
        		self.successMessage = "Arquivo importado com sucesso!";
        	}, 
        	function (errResponse) {
        		console.error('Ocorreu um erro ao importar arquivo.');
        		self.errorMessage = 'Ocorreu um erro ao importar arquivo.';
        	});
    }

    function obterTodasPessoas(){
        PessoaService.obterTodasPessoas()
            .then(
            function(data) {
                self.listaPessoas = data;
            },
            function(errResponse){
                console.error('Error while fetching Users');
                self.successMessage = '';
        		self.errorMessage = 'Ocorreu um erro ao obter todas Pessoas. Tente novamente mais tarde.';
            }
        );
    }

    function incluirPessoa(pessoa){
        PessoaService.incluirPessoa(pessoa)
            .then(
    		obterTodasPessoas,
            function (response){
    			console.log('Pessoa cadastrada com sucesso.');
    			self.successMessage = 'Pessoa cadastrada com sucesso.';
        		self.errorMessage = '';
    		},
            function(errResponse){
                console.error('Ocorreu um erro ao criar uma nova pessoa.');
                self.successMessage = '';
        		self.errorMessage = 'Ocorreu um erro ao criar uma nova Pessoa. Tente novamente mais tarde.';
            }
        );
    }

    function alterarPessoa(pessoa, id){
        PessoaService.alterarPessoa(pessoa, id)
            .then(
    		obterTodasPessoas,
            function (response){
    			console.log('Pessoa atualizada com sucesso.');
    			self.successMessage = 'Pessoa atualizada com sucesso';
        		self.errorMessage = '';
    		},
            function(errResponse){
                console.error('Ocorreu um erro ao atualizar a pessoa.');
                self.successMessage = '';
        		self.errorMessage = 'Ocorreu um erro ao atualizar a Pessoa. Tente novamente mais tarde.';
            }
        );
    }

    function excluirPessoa(id){
        PessoaService.excluirPessoa(id)
            .then(
    		obterTodasPessoas,
            function (response){
    			console.log('Pessoa excluida com sucesso.');
    			self.successMessage = 'Pessoa excluida com sucesso.';
        		self.errorMessage = '';
    		},
            function(errResponse){
                console.error('Ocorreu um erro ao deletar a Pessoa.');
                self.successMessage = '';
        		self.errorMessage = 'Ocorreu um erro ao deletar a Pessoa. Tente novamente mais tarde.';
            }
        );
    }

    function submit() {
        if(self.pessoa.id===null){
            console.log('Salvando nova Pessoa', self.pessoa);
            incluirPessoa(self.pessoa);
        }else{
            alterarPessoa(self.pessoa, self.pessoa.id);
            console.log('Alterando nova Pessoa: id ', self.pessoa.id);
        }
        reset();
    }

    function edit(id){
    	$scope.exibirCadastroPessoa = true;
        console.log('id a ser editado', id);
        for(var i = 0; i < self.listaPessoas.length; i++){
            if(self.listaPessoas[i].id === id) {
                self.pessoa = angular.copy(self.listaPessoas[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id a ser deletado', id);
        if(self.pessoa.id === id) {//clean form if the pessoa to be deleted is shown there.
            reset();
        }
        excluirPessoa(id);
    }


    function reset(){
    	self.successMessage='';
		self.errorMessage='';
		self.pessoa={id:null, nome:'', dataNascimento:'', email:'', telefone:''};
		$scope.myForm.$setPristine(); //reset Form
		$scope.exibirCadastroPessoa = false;
    }
    
}]);
