$(document).ready(function(){
	mascara();
	  $.blockUI.defaults.overlayCSS.opacity=0;
	  $.blockUI.defaults.message='Aguarde um pouco';
	PrimeFaces.locales['pt'] = {
	            closeText: 'Fechar',
	            prevText: 'Anterior',
	            nextText: 'Próximo',
	            currentText: 'Começo',
	            monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	            monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Des'],
	            dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	            dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
	            dayNamesMin: ['D','S','T','Q','Q','S','S'],
	            weekHeader: 'Semana',
	            firstDay: 1,
	            isRTL: false,
	            showMonthAfterYear: false,
	            yearSuffix: '',
	            timeOnlyTitle: 'Só Horas',
	            timeText: 'Tempo',
	            hourText: 'Hora',
	            minuteText: 'Minuto',
	            secondText: 'Segundo',
	            currentText: 'Data Atual',
	            ampm: false,
	            month: 'Mês',
	            week: 'Semana',
	            day: 'Dia',
	            allDayText : 'Todo Dia'
	        };
});

function mascara(){
	  $('.date').mask('00/00/0000');
	  $('.cpf').mask('000.000.000-00', {reverse: true});
	  $('.telefone').mask('(00) 0000-0000');
	  $('.prontuario').mask('000000000000000');
	  $('.numero').mask('000000000000000');
	  $('.cep').mask('00000-000');
	  $('.dinheiro').mask("#.##0,00", {reverse: true});
	  $('.cnpj').mask("00.000.000/0000-00", {reverse: true});

}


function closeDialogIfSucess(xhr, status, args, dialogWidget, dialogId) {
    if (args.validationFailed || args.KEEP_DIALOG_OPENED) {
        jQuery('#'+dialogId).effect("bounce", {times : 4, distance : 20}, 100);
    } 
    else {
    dialogWidget.hide();
    }
}