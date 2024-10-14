#! /bin/bash

javadoc japedidos -d $(pwd)/JaPedidos/javadoc -sourcepath $(pwd)/JaPedidos/src/main/java/ -subpackages japedidos.produto:japedidos.bd:japedidos.clientes:japedidos.pedidos:japedidos.relatorios:japedidos.usuario
