CREATE VIEW `listaTodosProdutos` AS
  select
  `p`.`id` AS `id`,
  `p`.`nome` AS `nome`,
  `c`.`nome` AS `categoria`,
  `p`.`preco_venda` AS `preco_venda`,
  `p`.`preco_custo` AS `preco_custo`,
  `u`.`abreviacao` AS `unidade`,
  `p`.`estado` AS `estado`
  from ((`produto` `p`
    left join `categoria` `c` on(`p`.`id_categoria` = `c`.`id`))
    left join `unidade` `u` on(`p`.`id_unidade` = `u`.`id`));

