package com.ITTDAM.bookncut.Adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;//declara el listener para cuando la fecha es elegida

    private int initialYear  = -1;//declara el año inicial en negativo para evitar problemas
    private int initialMonth = -1;//declara el mes inicial en negativo para evitar problemas
    private int initialDay = -1;//declara el dia inicial en negativo para evitar problemas

    //funcion encargada de crear una nuueva instancia del DatePickerDialog donde necesita de parametros un listener
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();//crea el fragmento para hacer el datepicker
        fragment.setListener(listener);//setea el listener al fragmento
        return fragment;//devuelve el fragmento
    }
    //sobrecarga a la nueva instancia del datepicker por si hay valores predeterminados
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener,int year,int month,int day) {
        DatePickerFragment fragment = new DatePickerFragment();//crea el fragmento para hacer el datepicker
        fragment.setListener(listener);//setea el listener al fragmento
        fragment.initialYear=year;//asigna el valor del año inicial
        fragment.initialMonth=month;//asigna el valor del mes inicial
        fragment.initialDay=day;//asigna el valor del dia inicial
        return fragment;//devuelve el fragmento
    }
    //funcion para asignar el listener
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
    //funcion que se ejecuta cuando se crea el dialog del fragmento
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Usa la fecha actual como default
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        // Año inicial seleccionado si es igual a -1 le asigna valor
        if (initialYear == -1)
            initialYear = year - 18;

        // Mes inicial seleccionado si es igual a -1 le asigna valor
        if (initialMonth == -1)
            initialMonth = c.get(Calendar.MONTH);

        // Dia inicial seleccionado si es igual a -1 le asigna valor
        if (initialDay == -1)
            initialDay = c.get(Calendar.DAY_OF_MONTH);


        //Crea el datepickerdialog
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),listener,  initialYear, initialMonth, initialDay);
        // fecha minima y maxima
        c.setTime(c.getTime());//obtiene el tiempo y lo asigna
        //asgina la fecha minima
        dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.set(Calendar.MONTH, c.getTime().getMonth()+1);
        //asgina la fecha maxima
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        // devuelve el dialogo
        return dialog;
    }

}
