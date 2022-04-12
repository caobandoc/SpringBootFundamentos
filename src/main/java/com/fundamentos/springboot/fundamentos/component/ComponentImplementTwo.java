package com.fundamentos.springboot.fundamentos.component;

import org.springframework.stereotype.Component;

@Component
public class ComponentImplementTwo implements ComponentDepenedency{

    @Override
    public void saludar() {
        System.out.println("Hola desde Componente 2");
    }
}
