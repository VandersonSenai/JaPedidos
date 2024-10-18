#! /bin/bash
cd JaPedidos/
javadoc japedidos -d "$(pwd)"/javadoc -sourcepath "$(pwd)"/src/main/java/ -private -subpackages japedidos.produto:japedidos.bd:japedidos.clientes:japedidos.pedidos:japedidos.relatorios:japedidos.usuario
