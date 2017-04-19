<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>CRUD Pessoa - Desafio entrevista iMedicina</title>  
    <style>
      .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }

    </style>
    
    <script type="text/javascript">
	    $('telefone').mask("(99) 9999-9999?9").ready(function(event) {
	        var target, phone, element;
	        target = (event.currentTarget) ? event.currentTarget : event.srcElement;
	        phone = target.value.replace(/\D/g, '');
	        element = $(target);
	        element.unmask();
	        if(phone.length > 10) {
	            element.mask("(99) 99999-999?9");
	        } else {
	            element.mask("(99) 9999-9999?9");  
	        }
	    });
		
	    $('telefone').focusout(function(){
	        var phone, element;
	        element = $(this);
	        element.unmask();
	        phone = element.val().replace(/\D/g, '');
	        if(phone.length > 10) {
	            element.mask("(99) 99999-999?9");
	        } else {
	            element.mask("(99) 9999-9999?9");
	        }
	    }).trigger('focusout');
    </script>
    
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="PessoaController as ctrl">
      
      		<div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
            
      	  	<div class="row">
			  <div class="col-lg-9">
				<div class="input-group">
			      <input type="text" class="form-control" placeholder="Pesquisar por..." ng-model="searchKeyword">
			      <span class="input-group-btn">
			        <button class="btn btn-default" type="button">Buscar</button>
			      </span>
			    </div>			    
			  </div>
			  	<div class="col-lg-3">
			    	<button type="button" ng-click="exibirCadastroPessoa = !exibirCadastroPessoa" ng-model="exibirCadastroPessoa" class="btn btn-success btn-md">Adicionar Novo</button>
	  			</div>
			</div>
			
			<br />
			
			<div class="row">
            	<div class="col-lg-9">
           			<input type="file" demo-file-model="myFile"  class="form-control" id ="myFileField" name="file"/>
		  		</div>
		  		<div class="col-lg-3">
		  			<button type="button" ng-click="ctrl.uploadFile()" class = "btn btn-primary">Importar Arquivo</button>
		  		</div>
		  	</div>
			
			<br/>
      
          <div class="panel panel-default" ng-show="exibirCadastroPessoa">
              <div class="panel-heading"><span class="lead">Formulario de Registro de Pessoas</span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.pessoa.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">* Nome</label>
                              <div class="col-md-6">
                                  <input type="text" ng-model="ctrl.pessoa.nome" name="nome" class="nome form-control input-sm" 
                                  	placeholder="Digite seu nome." required ng-minlength="3"/>
                                  
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.nome.$error.required">Campo obrigatório.<br /></span>
                                      <span ng-show="myForm.nome.$error.minlength">O tamanho mínimo é de 3 caracteres.<br /></span>
                                      <span ng-show="myForm.nome.$invalid && !myForm.nome.$error.required">Este campo é inválido!</span>
                                  </div>
                              </div>
                              
                              <label class="col-md-2 control-lable" for="file">* Data Nascimento</label>
                              <div class="col-md-2">
                                  <input type="date" name="data" ng-model="ctrl.pessoa.dataNascimento" class="data form-control input-sm"
       									placeholder="dd/MM/yyyy" required size="10" ng-change="m.user.date=dt.toISOString()"/>
       									
    								<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.data.$error.required">Campo obrigatório.<br /></span>
                                      <span ng-show="myForm.data.$invalid && !myForm.data.$error.required">Este campo é inválido!</span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">* Email</label>
                              <div class="col-md-10">
                                  <input type="email" ng-model="ctrl.pessoa.email" name="email" class="email form-control input-sm" 
                                  	placeholder="Digite seu email." required/>
                                  
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">Campo obrigatório.<br /></span>
                                      <span ng-show="myForm.email.$invalid && !myForm.email.$error.required">Este campo é inválido!</span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">* Telefone</label>
                              <div class="col-md-7">
                                  <input type="tel" ng-model="ctrl.pessoa.telefone" class="form-control input-sm" name="telefone"
                                  	placeholder="Digite seu telefone." required id="telefone"/>
                                  	
                                  	<div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.telefone.$error.required">Campo obrigatório.<br /></span>
                                      <span ng-show="myForm.email.$invalid">Este campo é inválido!</span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.pessoa.id ? 'Incluir' : 'Salvar'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Limpar</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">Lista de Pessoas</span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>Nome</th>
                              <th>Email</th>
                              <th>Telefone</th>
                              <th width="20%">Operações</th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="pessoa in ctrl.listaPessoas | filter: searchKeyword">
                              <td><span ng-bind="pessoa.nome"></span></td>
                              <td><span ng-bind="pessoa.email"></span></td>
                              <td><span ng-bind="pessoa.telefone"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(pessoa.id)" class="btn btn-success custom-width">Editar</button>  
                              <button type="button" ng-click="ctrl.remove(pessoa.id)" class="btn btn-danger custom-width">Excluir</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
      
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/pessoa_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/pessoa_controller.js' />"></script>
      <script src="<c:url value='/static/js/util/jquery-3.2.1.min.js' />"></script>
      <script src="<c:url value='/static/js/util/jquery.maskedinput.js' />"></script>
  </body>
</html>