'use strict';

angular.module('myApp').factory('PessoaService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/CrudPessoa/pessoa/';

    var factory = {
        obterTodasPessoas: obterTodasPessoas,
        incluirPessoa: incluirPessoa,
        alterarPessoa:alterarPessoa,
        excluirPessoa:excluirPessoa,
        uploadFileToUrl:uploadFileToUrl
    };

    return factory;
    
    function uploadFileToUrl(file) {
        //FormData, object of key/value pair for form fields and values
        var fileFormData = new FormData();
        fileFormData.append('file', file);

        var deffered = $q.defer();
        $http.post(REST_SERVICE_URI + "importacao/", fileFormData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}

        }).success(function (response) {
            deffered.resolve(response);

        }).error(function (response) {
            deffered.reject(response);
        });

        return deffered.promise;
    }

    function obterTodasPessoas() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Ocorreu um erro ao obter todas Pessoa.');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function incluirPessoa(pessoa) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, pessoa)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Ocorreu um erro ao incluir a Pessoa.');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function alterarPessoa(pessoa, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, pessoa)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Ocorreu um erro ao alterar a Pessoa.');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function excluirPessoa(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Ocorreu um erro ao deletar a Pessoa.');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
