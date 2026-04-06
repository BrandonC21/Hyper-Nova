package org.example.hypernova.dto;

public class FinalizarContrato {
        private String observaciones;
        private double montoExtra;
        private String descripcionExtra;
        
        public String getObservaciones() {
            return observaciones;
        }
        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
        public double getMontoExtra() {
            return montoExtra;
        }
        public void setMontoExtra(double montoExtra) {
            this.montoExtra = montoExtra;
        }
        public String getDescripcionExtra() {
            return descripcionExtra;
        }
        public void setDescripcionExtra(String descripcionExtra) {
            this.descripcionExtra = descripcionExtra;
        }

        
    }