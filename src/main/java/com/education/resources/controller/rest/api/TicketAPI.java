//package com.education.resources.controller.rest.api;
//
//import com.education.resources.bean.entity.Ticket;
//import com.education.resources.bean.from.PageForm;
//import com.education.resources.service.TicketService;
//import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//@Api(tags = "小程序一键求助接口")
//@RestController
//@RequestMapping("/api/ma/user/ticket")
//public class TicketAPI {
//
//    @Autowired
//    TicketService ticketService;
//
//    @GetMapping
//    @ApiOperation("获取我的求助")
//    public API<Page<Ticket>>  getTicket(@RequestParam(required = false) Ticket.Status status, PageForm pageForm){
//        return API.ok(ticketService.getTickets(status,pageForm));
//    }
//
//    @GetMapping(value = "detail")
//    @ApiOperation("求助详情")
//    public API<Ticket>  getTicketDetail(Integer id){
//        return API.ok(ticketService.getTicketDetail(id));
//    }
//
//    @PostMapping
//    @ApiOperation("添加求助")
//    public API<Ticket>  saveTicket(@RequestBody Ticket ticket){
//        return API.ok(ticketService.saveTicket(ticket));
//    }
//
//
//}
