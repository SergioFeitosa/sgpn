$(document).ready( function () {
	 var table = $('#atributosTable').DataTable({
			"sAjaxSource": "/atributos",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "atributoPK"},
			    { "mData": "atributoNome" },
				{ "mData": "atributoDescricao" }
	 	]				
					
			
	 })

});

