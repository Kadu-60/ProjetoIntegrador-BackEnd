package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.Item_pedidoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.Item_pedido_keyDTO;
import br.com.rd.ProjetoIntegrador.model.dto.PedidoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Item_pedido;
import br.com.rd.ProjetoIntegrador.service.Item_pedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/item_pedido")
public class Item_pedidoController {
    @Autowired
    Item_pedidoService item_pedidoService;

    @GetMapping
    public List<Item_pedidoDTO> findAll(){
        return this.item_pedidoService.findAll();
    }
    @GetMapping("/findById")
    public Item_pedidoDTO findById(@RequestBody Item_pedido_keyDTO item_pedido_keyDTO){
        return this.item_pedidoService.findById(item_pedido_keyDTO);
    }
    @GetMapping("/findBypedido/{id}")
    public List<Item_pedidoDTO> findByidpedido(@PathVariable("id")Long id){
        return this.item_pedidoService.findByidpedido(id);
    }
//    @PutMapping("/{item}/{id_pedido}")
//    public Item_pedidoDTO updateById(@PathVariable("id_pedido") Long id_pedido,@PathVariable("item") Long id_item, @RequestBody Item_pedidoDTO item_pedidoDTO){
//        Item_pedido_keyDTO item_pedido_keyDTO = new Item_pedido_keyDTO();
//        PedidoDTO pedidoDTO = new PedidoDTO();
//        pedidoDTO.setId(id_pedido);
//        item_pedido_keyDTO.setPedido(pedidoDTO);
//        item_pedido_keyDTO.setItem(id_item);
//        return this.item_pedidoService.update(item_pedidoDTO, item_pedido_keyDTO);
//    }
    @PostMapping
    public  Item_pedidoDTO create(@RequestBody Item_pedidoDTO dto){
        return this.item_pedidoService.create(dto);
    }
    @DeleteMapping("/{item}/{id_pedido}")
    public void delete(@PathVariable("id_pedido") Long id_pedido,@PathVariable("item") Long id_item){
        Item_pedido_keyDTO item_pedido_keyDTO = new Item_pedido_keyDTO();
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(id_pedido);
        item_pedido_keyDTO.setPedido(pedidoDTO);
        item_pedido_keyDTO.setItem(id_item);
        this.item_pedidoService.delete(item_pedido_keyDTO);
    }

}
