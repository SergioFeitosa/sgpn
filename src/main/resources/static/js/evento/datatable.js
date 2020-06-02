$(document).ready( function () {
	 var table = $('#eventosTable').DataTable({
			"sAjaxSource": "/eventos",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "eventoPK"},
			    { "mData": "eventoNome" },
				{ "mData": "eventoDescricao" },
	 	]				
					
			
	 })

});

