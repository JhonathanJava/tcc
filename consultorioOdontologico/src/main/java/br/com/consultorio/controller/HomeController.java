package br.com.consultorio.controller;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.consultorio.dao.TituloDAO;
 
@Named
@ViewScoped
public class HomeController implements Serializable {
 
	private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private LineChartModel lineModel3;
    private List<Map<Object, Object>> titulosReceber;
    private List<Map<Object, Object>> titulosPagar;
    
    @Inject
    private TituloDAO dao;
    @PostConstruct
    public void init() {
        createLineModels();
    }
 
    public LineChartModel getLineModel3() {
		return lineModel3;
	}
    
    public LineChartModel getLineModel2() {
        return lineModel2;
    }
    
    public LineChartModel getLineModel1() {
		return lineModel1;
	}
     
    private void createLineModels() {
    	 lineModel1 = initPagamento();
         lineModel1.setTitle("Gráfico Pagamento");
         lineModel1.setLegendPosition("e");
         lineModel1.setShowPointLabels(true);
         lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Dia do Mês"));
         lineModel1.setAnimate(true);
         lineModel1.setSeriesColors("FF0000");
         Axis yAxis = lineModel1.getAxis(AxisType.Y);
         yAxis.setLabel("Valor Total do Dia");
         
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Gráfico Recebimento");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Dia do Mês"));
        lineModel2.setAnimate(true);
        lineModel2.setSeriesColors("00FF00");
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Valor Total do Dia");
        
        
        lineModel3 = initPagamentoRecebimento();
        lineModel3.setTitle("Recebimento X Pagamento");
        lineModel3.setLegendPosition("e");
        lineModel3.setShowPointLabels(true);
        lineModel3.getAxes().put(AxisType.X, new CategoryAxis("Dia do Mês"));
        lineModel3.setAnimate(true);
        lineModel3.setSeriesColors("00FF00,FF0000");
        yAxis = lineModel3.getAxis(AxisType.Y);
        yAxis.setLabel("Valor Total do Dia");
    }
    
    private LineChartModel initPagamentoRecebimento() {
    	 LineChartModel model = new LineChartModel();
    	 
         ChartSeries pagar = new ChartSeries();
         pagar.setLabel("Á Pagar");
         
         ChartSeries receber = new ChartSeries();
         receber.setLabel("Á Receber");
         
        Calendar dataAtual = Calendar.getInstance();
 		Calendar primeiroDia = Calendar.getInstance();
 		Calendar ultimoDia = Calendar.getInstance();
 		//1º dia do mês atual
 		primeiroDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
 		primeiroDia.add(Calendar.DAY_OF_YEAR, 1);
 		System.out.println(primeiroDia.getTime());
 		//Ultimo dia do mês atual
 		ultimoDia.add(Calendar.MONTH, 1);
 		ultimoDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
 		System.out.println(ultimoDia.getTime());
 		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
         
         this.titulosPagar = dao.getSqlListMap("select sum(tit_valor) valor , Day(tit_vencimento) dia from Titulo where tit_tipo = 'D' and tit_vencimento >= '"+formatador.format(primeiroDia.getTime())+"' and tit_vencimento <= '"+formatador.format(ultimoDia.getTime())+"'  group by 2 order by Day(tit_vencimento)");

         for (Map<Object, Object> titulo : titulosPagar) {
        	 pagar.set(titulo.get("dia").toString(), Double.parseDouble(titulo.get("valor").toString()));
         	System.out.println(titulo);
 		}
         
         this.titulosReceber = dao.getSqlListMap("select sum(tit_valor) valor , Day(tit_vencimento) dia from Titulo where tit_tipo = 'C' and tit_vencimento >= '"+formatador.format(primeiroDia.getTime())+"' and tit_vencimento <= '"+formatador.format(ultimoDia.getTime())+"'   group by 2 order by Day(tit_vencimento)");

         for (Map<Object, Object> titulo : titulosReceber) {
         	receber.set(titulo.get("dia").toString(), Double.parseDouble(titulo.get("valor").toString()));
         	System.out.println(titulo);
 		}
         model.addSeries(receber);
         model.addSeries(pagar);
         
		return model;
	}

	private LineChartModel initPagamento() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Á Pagar");
        
        Calendar dataAtual = Calendar.getInstance();
		Calendar primeiroDia = Calendar.getInstance();
		Calendar ultimoDia = Calendar.getInstance();
		//1º dia do mês atual
		primeiroDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
		primeiroDia.add(Calendar.DAY_OF_YEAR, 1);
		System.out.println(primeiroDia.getTime());
		//Ultimo dia do mês atual
		ultimoDia.add(Calendar.MONTH, 1);
		ultimoDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
		System.out.println(ultimoDia.getTime());
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        this.titulosPagar = dao.getSqlListMap("select sum(tit_valor) valor , Day(tit_vencimento) dia from Titulo where tit_tipo = 'D' and tit_vencimento >= '"+formatador.format(primeiroDia.getTime())+"' and tit_vencimento <= '"+formatador.format(ultimoDia.getTime())+"' group by 2 ");

        for (Map<Object, Object> titulo : titulosPagar) {
        	boys.set(titulo.get("dia").toString(), Double.parseDouble(titulo.get("valor").toString()));
        	System.out.println(titulo);
		}
        model.addSeries(boys);
         
        return model;
    }
     
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Á Receber");
        
        Calendar dataAtual = Calendar.getInstance();
		Calendar primeiroDia = Calendar.getInstance();
		Calendar ultimoDia = Calendar.getInstance();
		//1º dia do mês atual
		primeiroDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
		primeiroDia.add(Calendar.DAY_OF_YEAR, 1);
		System.out.println(primeiroDia.getTime());
		//Ultimo dia do mês atual
		ultimoDia.add(Calendar.MONTH, 1);
		ultimoDia.add(Calendar.DAY_OF_MONTH, -dataAtual.get(Calendar.DAY_OF_MONTH));
		System.out.println(ultimoDia.getTime());
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        this.titulosReceber = dao.getSqlListMap("select sum(tit_valor) valor , Day(tit_vencimento) dia from Titulo where tit_tipo = 'C' and tit_vencimento >= '"+formatador.format(primeiroDia.getTime())+"' and tit_vencimento <= '"+formatador.format(ultimoDia.getTime())+"' group by 2 ");

        for (Map<Object, Object> titulo : titulosReceber) {
        	boys.set(titulo.get("dia").toString(), Double.parseDouble(titulo.get("valor").toString()));
        	System.out.println(titulo);
		}
        model.addSeries(boys);
         
        return model;
    }
}