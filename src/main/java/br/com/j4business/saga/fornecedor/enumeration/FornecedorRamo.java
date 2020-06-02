package br.com.j4business.saga.fornecedor.enumeration;

public enum FornecedorRamo {
	
	ACADEMIA_DE_ESPORTES_E_ARTES_MARCIAIS("ACADEMIA_DE_ESPORTES_E_ARTES_MARCIAIS"),
	AÇUCAR_E_ÁLCOOL("AÇUCAR_E_ÁLCOOL"),
	ADMINISTRAÇÃO_E_PARTICIPAÇÃO("ADMINISTRAÇÃO_E_PARTICIPAÇÃO"),
	AGÊNCIAS_DE_TURISMO_VIAGEM("AGÊNCIAS_DE_TURISMO_VIAGEM"),
	AGRICULTURA_PECUÁRIA_SILVICULTURA("AGRICULTURA_PECUÁRIA_SILVICULTURA"),
	ALIMENTOS("ALIMENTOS"),
	ARQUITETURA_PAISAGISMO_URBANISMO("ARQUITETURA_PAISAGISMO_URBANISMO"),
	ASSESSORIA_DE_IMPRENSA("ASSESSORIA_DE_IMPRENSA"),
	AUTOMAÇÃO("AUTOMAÇÃO"),
	AUTOMOTIVO("AUTOMOTIVO"),
	BANCÁRIO_FINANCEIRO("BANCÁRIO_FINANCEIRO"),
	BEBIDAS("BEBIDAS"),
	BENS_DE_CAPITAL("BENS_DE_CAPITAL"),
	BENS_DE_CONSUMO("BENS_DE_CONSUMO"),
	BORRACHA("BORRACHA"),
	CAFÉ_PÓ("CAFÉ_PÓ"),
	CALÇADOS("CALÇADOS"),
	COMÉRCIO_ATACADISTA("COMÉRCIO_ATACADISTA"),
	COMÉRCIO_VAREJISTA("COMÉRCIO_VAREJISTA"),
	COMUNICAÇÃO("COMUNICAÇÃO"),
	CONCESSIONÁRIAS___AUTO_PEÇAS("CONCESSIONÁRIAS___AUTO_PEÇAS"),
	CONSTRUÇÃO_CIVIL("CONSTRUÇÃO_CIVIL"),
	CONSULTORIA_GERAL("CONSULTORIA_GERAL"),
	CONTABILIDADE_AUDITORIA("CONTABILIDADE_AUDITORIA"),
	CORRETAGEM_IMÓVEIS("CORRETAGEM_IMÓVEIS"),
	CORRETAGEM_SEGUROS("CORRETAGEM_SEGUROS"),
	CORRETAGEM_DE_TÍTULOS_E_VALORES_IMOBILIÁRIOS("CORRETAGEM_DE_TÍTULOS_E_VALORES_IMOBILIÁRIOS"),
	COSMÉTICOS("COSMÉTICOS"),
	DIVERSÃO_ENTRETENIMENTO("DIVERSÃO_ENTRETENIMENTO"),
	EDUCAÇÃO_IDIOMAS("EDUCAÇÃO_IDIOMAS"),
	ELETRÔNICA__ELETROELETRÔNICA__ELETRODOMÉSTICOS("ELETRÔNICA__ELETROELETRÔNICA__ELETRODOMÉSTICOS"),
	EMBALAGENS("EMBALAGENS"),
	ENERGIA_ELETRICIDADE("ENERGIA_ELETRICIDADE"),
	ENGENHARIA("ENGENHARIA"),
	EQUIPAMENTOS_INDUSTRIAIS("EQUIPAMENTOS_INDUSTRIAIS"),
	EQUIPAMENTOS_MÉDICOS___PRECISÃO("EQUIPAMENTOS_MÉDICOS___PRECISÃO"),
	ESTÉTICA_ACADEMIAS("ESTÉTICA_ACADEMIAS"),
	EVENTOS("EVENTOS"),
	FARMACÊUTICA__VETERINÁRIA("FARMACÊUTICA__VETERINÁRIA"),
	FINANCEIRAS("FINANCEIRAS"),
	GRÁFICA_EDITORAS("GRÁFICA_EDITORAS"),
	IMPORTAÇÃO_EXPORTAÇÃO("IMPORTAÇÃO_EXPORTAÇÃO"),
	INCORPORADORA("INCORPORADORA"),
	INDÚSTRIAS("INDÚSTRIAS"),
	INFORMÁTICA("INFORMÁTICA"),
	INTERNET_SITES("INTERNET_SITES"),
	JORNAIS("JORNAIS"),
	JURÍDICA("JURÍDICA"),
	LOGÍSTICA_ARMAZÉNS("LOGÍSTICA_ARMAZÉNS"),
	MADEIRAS("MADEIRAS"),
	MATERIAIS_DE_CONSTRUÇÃO("MATERIAIS_DE_CONSTRUÇÃO"),
	MATERIAIS_DE_ESCRITÓRIO("MATERIAIS_DE_ESCRITÓRIO"),
	MECÂNICA_MANUTENÇÃO("MECÂNICA_MANUTENÇÃO"),
	METALÚRGICA_SIDERÚRGICA("METALÚRGICA_SIDERÚRGICA"),
	MINERAÇÃO("MINERAÇÃO"),
	MÓVEIS_E_ARTEFATOS_DE_DECORAÇÃO("MÓVEIS_E_ARTEFATOS_DE_DECORAÇÃO"),
	ORGÃOS_PÚBLICOS("ORGÃOS_PÚBLICOS"),
	OUTROS("OUTROS"),
	PAPEL_E_DERIVADOS("PAPEL_E_DERIVADOS"),
	PETROQUÍMICO__PETRÓLEO("PETROQUÍMICO__PETRÓLEO"),
	PLÁSTICOS("PLÁSTICOS"),
	PRESTADORA_DE_SERVIÇOS("PRESTADORA_DE_SERVIÇOS"),
	PUBLICIDADE___PROPAGANDA("PUBLICIDADE___PROPAGANDA"),
	RECURSOS_HUMANOS("RECURSOS_HUMANOS"),
	RELAÇLÕES_PÚBLICAS("RELAÇLÕES_PÚBLICAS"),
	REPRESENTAÇÃO_COMERCIAL("REPRESENTAÇÃO_COMERCIAL"),
	RESTAURANTE__INDUSTRIAL__FAST_FOOD("RESTAURANTE__INDUSTRIAL__FAST_FOOD"),
	SAÚDE("SAÚDE"),
	SEGURANÇA_PATRIMONIAL("SEGURANÇA_PATRIMONIAL"),
	SEGUROS__PREVIDÊNCIA_PRIVADA("SEGUROS__PREVIDÊNCIA_PRIVADA"),
	SINDICATOS___ASSOCIAÇÕES___ONGS("SINDICATOS___ASSOCIAÇÕES___ONGS"),
	SUPERMERCADO___HIPERMERCADO("SUPERMERCADO___HIPERMERCADO"),
	TELECOMUNICAÇÕES("TELECOMUNICAÇÕES"),
	TELEMARKETING__CALL_CENTER("TELEMARKETING__CALL_CENTER"),
	TÊXTIL__COURO("TÊXTIL__COURO"),
	TRANSPORTES("TRANSPORTES"),
	TURISMO__HOTELARIA("TURISMO__HOTELARIA");

	private String nome;

    private FornecedorRamo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}