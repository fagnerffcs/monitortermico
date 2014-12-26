package br.com.practicalsolutions.monitortermico.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;
import br.com.practicalsolutions.monitortermico.facade.Fachada;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;
import br.com.practicalsolutions.monitortermico.model.Protocolo;
import br.com.practicalsolutions.monitortermico.model.Status;
import br.com.practicalsolutions.monitortermico.model.TipoAlerta;

@ApplicationScoped
public class ControladorEquipamento {

	@Inject
    private FacesContext facesContext;

    @Inject
    private CadastroEquipamento cadastroEquipamento;
    
    @Inject
    private Fachada fachada;
    
    private static Logger log = LoggerFactory.getLogger(ControladorEquipamento.class);

    private Equipamento novoEquipamento;
    
    static Equipamento selecionado;
    
    private Equipamento editado;
    
    private LineChartModel lineChartModel;
    
	@SuppressWarnings("unused")
	private Status[] status;
    
	@SuppressWarnings("unused")
	private Protocolo[] protocolo;
	
	@SuppressWarnings("unused")
	private TipoAlerta[] tipoAlerta;
	
	private Long id;

	@Produces
    @Named
    public Equipamento getNovoEquipamento() {
        return novoEquipamento;
    }
	
	public CadastroEquipamento getCadastroEquipamento() {
		return cadastroEquipamento;
	}

	public void setCadastroEquipamento(CadastroEquipamento cadastroEquipamento) {
		this.cadastroEquipamento = cadastroEquipamento;
	}	

	public Status[] getStatus() {
		return Status.values();
	}

	public void setStatus(Status[] status) {
		this.status = status;
	}

	public Protocolo[] getProtocolo() {
		return Protocolo.values();
	}

	public void setProtocolo(Protocolo[] protocolo) {
		this.protocolo = protocolo;
	}

	public TipoAlerta[] getTipoAlerta() {
		return TipoAlerta.values();
	}

	public void setTipoALerta(TipoAlerta[] tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Equipamento getSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado(Equipamento equ){
		selecionado = equ;
	}
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LineChartModel getLineChartModel() {
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DATE, -1);
		List<Medicao> lista = fachada.getControladorMedicao().getCadastroMedicao()
															 .medicoesPorEquipamentoPorPeriodo(getSelecionado().getId(), 
																	 						   inicio.getTime(), 
																	 						   new Date(), 20);
		ControladorGrafico controladorGrafico = fachada.getControladorGrafico();
		lineChartModel = controladorGrafico.generateLineChartModel(lista);
		return lineChartModel;
	}

	public void setLineChartModel(LineChartModel lineChartModel) {
		this.lineChartModel = lineChartModel;
	}	

	public Equipamento getEditado() {
		if(id!=null){
			editado = getCadastroEquipamento().buscarPorId(id);
		}
		return editado;
	}

	public void setEditado(Equipamento editado) {
		this.editado = editado;
	}

	public void register() {
        try {
        	getCadastroEquipamento().register(novoEquipamento);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado!", "Registration successful"));
            initNovoEquipamento();
        } catch (Exception e) {
        	log.error("Erro ao tentar cadastrar o equipamento " + novoEquipamento.getDescricao());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Not Registered due to error in data entered!", "Registration unsuccessful"));
        }
    }

    @PostConstruct
    public void initNovoEquipamento() {
        novoEquipamento = new Equipamento();
        editado = new Equipamento();
    }
    
	public String goDetail(Equipamento e){
		selecionado = e;
		return "SUCESSO";
	}
	
	public String voltar(){
		return "SUCESSO";
	}
	
	public void desativarEquipamento(Equipamento e){
		getCadastroEquipamento().desativarEquipamento(e);
	}
	
	public void reativarEquipamento(Equipamento e){
		getCadastroEquipamento().reativarEquipamento(e);
	}
	
	public String editar(){
		return "SUCESSO";
	}

}
