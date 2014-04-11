package br.com.practicalsolutions.monitortermico.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;
import br.com.practicalsolutions.monitortermico.model.Status;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;
import br.com.practicalsolutions.monitortermico.service.MedicaoRegistration;

@Model
public class EquipamentoController {

	@Inject
    private FacesContext facesContext;

    @Inject
    private EquipamentoRegistration equipamentoRegistration;
    
    @Inject
    private MedicaoRegistration medicaoRegistration;
    
    private static Logger log = LoggerFactory.getLogger(EquipamentoController.class);

    private Equipamento novoEquipamento;
    
    private StreamedContent tempChart;
    
    private StreamedContent umidadeChart;
    
    static Equipamento selecionado;
    
    static CartesianChartModel linearModel;
    
    @SuppressWarnings("unused")
	private Status[] status;

    @Produces
    @Named
    public Equipamento getNovoEquipamento() {
        return novoEquipamento;
    }

    public void register() {
        try {
        	equipamentoRegistration.register(novoEquipamento);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            initNovoEquipamento();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Not Registered due to error in data entered!", "Registration unsuccessful"));
        }
    }

    @PostConstruct
    public void initNovoEquipamento() {
        novoEquipamento = new Equipamento();
    }

	public Status[] getStatus() {
		return Status.values();
	}

	public void setStatus(Status[] status) {
		this.status = status;
	}

	public String goDetail(Equipamento e){
		selecionado = e;
		return "SUCESSO";
	}
	
	public String voltar(){
		return "SUCESSO";
	}

	public Equipamento getSelecionado() {
		return selecionado;
	}
	
	public void setSelecionado(Equipamento equ){
		selecionado = equ;
	}

	public StreamedContent getTempChart() {
		return buildTemperaturaChart(getSelecionado().getId());
	}

	public void setTempChart(StreamedContent tempChart) {
		this.tempChart = tempChart;
	}
	
	public StreamedContent getUmidadeChart() {
		return buildUmidadeChart(getSelecionado().getId());
	}
	
	public void setUmidadeChart(StreamedContent umidadeChart) {
		this.umidadeChart = umidadeChart;
	}	

	public DefaultStreamedContent buildTemperaturaChart(Long id) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(medicaoRegistration!=null){
			int qtde = 1;
			Calendar inicio = Calendar.getInstance();
			inicio.add(Calendar.DATE, -1);

			List<Medicao> lista = medicaoRegistration
								  .medicoesPorEquipamentoPorPeriodo(id, 
																	inicio.getTime(), 
																	new Date(),
																	20);
			for(Medicao m : lista){
				dataset.addValue(m.getTemperatura(), "°C", Integer.valueOf(qtde));
				qtde++;
			}
		}		
		JFreeChart freeChart = ChartFactory.createLineChart("Temperatura", "Medições", "°C", dataset, PlotOrientation.VERTICAL, false, true, false);
		freeChart.setBackgroundPaint(Color.WHITE);
		File chartFile = new File("/tmp/tempChart");
		try {
			ChartUtilities.saveChartAsPNG(chartFile, freeChart, 600, 300);
			tempChart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
			return (DefaultStreamedContent) tempChart;
		} catch (IOException e) {
			log.error("Erro criar arquivo dynamicChart na pasta tmp");
			return null;
		}
	}

	public DefaultStreamedContent buildUmidadeChart(Long id) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(medicaoRegistration!=null){
			int qtde = 1;
			Calendar inicio = Calendar.getInstance();
			inicio.add(Calendar.DATE, -1);		
			List<Medicao> lista = medicaoRegistration
					  .medicoesPorEquipamentoPorPeriodo(id, 
														inicio.getTime(), 
														new Date(),
														20);		
			
			for(Medicao m : lista){
				dataset.addValue(m.getUmidade(), "kg/m³", Integer.valueOf(qtde));
				qtde++;
			}
		}		
		JFreeChart freeChart = ChartFactory.createLineChart("Umidade", "Medições", "kg/m³", dataset, PlotOrientation.VERTICAL, false, true, false);
		freeChart.setBackgroundPaint(Color.WHITE);
		File chartFile = new File("/tmp/umidChart");
		try {
			ChartUtilities.saveChartAsPNG(chartFile, freeChart, 600, 300);
			umidadeChart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
			return (DefaultStreamedContent) umidadeChart;
		} catch (IOException e) {
			log.error("Erro criar arquivo dynamicChart na pasta tmp");
			return null;
		}
	}

	public CartesianChartModel getLinearModel() {
		linearModel = new CartesianChartModel();
		  
        LineChartSeries series1 = new LineChartSeries();  
        series1.setLabel("Temperatura");
        
        LineChartSeries series2 = new LineChartSeries();  
        series2.setLabel("Umidade");
        series2.setMarkerStyle("diamond");
        
		int qtde = 1;
		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DATE, -1);

		List<Medicao> lista = medicaoRegistration
							  .medicoesPorEquipamentoPorPeriodo(selecionado.getId(), 
																inicio.getTime(), 
																new Date(),
																20);
		for(Medicao m : lista){
			series1.set(qtde, m.getTemperatura());
			series2.set(qtde, m.getUmidade());
			qtde++;
		}        
        
        linearModel.addSeries(series1);  
        linearModel.addSeries(series2);        
        
		return linearModel;
	}
	
}
