package fdse21.group25.perfectlyfinelibrary.copyservice.controller;

import java.util.List;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fdse21.group25.perfectlyfinelibrary.common.client.AdminAuthServiceClient;
import fdse21.group25.perfectlyfinelibrary.common.event.ConfigEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.NotifyEvent;
import fdse21.group25.perfectlyfinelibrary.common.event.OrderEvent;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.copyservice.dto.AddedCopyDto;
import fdse21.group25.perfectlyfinelibrary.copyservice.entity.Copy;
import fdse21.group25.perfectlyfinelibrary.copyservice.service.CopyConfigService;
import fdse21.group25.perfectlyfinelibrary.copyservice.service.CopyService;
import fdse21.group25.perfectlyfinelibrary.copyservice.service.NotifyEventService;
import fdse21.group25.perfectlyfinelibrary.copyservice.service.OrderEventService;
import feign.FeignException.Unauthorized;

@RestController
@RequestMapping("/copies")
public class CopyController {
    private final AdminAuthServiceClient adminAuthServiceClient;
    private final CopyConfigService copyConfigService;
    private final CopyService copyService;
    private final NotifyEventService notifyEventService;
    private final OrderEventService orderEventService;

    public CopyController(AdminAuthServiceClient adminAuthServiceClient, CopyConfigService copyConfigService,
            CopyService copyService, NotifyEventService notifyEventService, OrderEventService orderEventService) {
        this.adminAuthServiceClient = adminAuthServiceClient;
        this.copyConfigService = copyConfigService;
        this.copyService = copyService;
        this.notifyEventService = notifyEventService;
        this.orderEventService = orderEventService;
    }

    @GetMapping("/{copyId}")
    Copy findCopyByCopyId(@PathVariable("copyId") Long copyId) throws NotFoundException {
        return copyService.findCopyByCopyId(copyId);
    }

    @GetMapping
    List<Copy> findCopies(@SpringQueryMap Copy copy) {
        return copyService.findCopies(copy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Copy> addNewCopy(@RequestBody AddedCopyDto dto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
            throws Unauthorized, NotFoundException {
        return copyService.addNewCopy(adminAuthServiceClient.checkToken(token), dto);
    }

    @PostMapping("/events/order")
    OrderEvent.CopyReply handleOrderEvent(@RequestBody OrderEvent event) {
        return orderEventService.handleOrderEvent(event);
    }

    @PostMapping("/evnets/config")
    void handleConfigEvent(@RequestBody ConfigEvent event) {
        copyConfigService.handleConfigEvent(event);
    }

    @PostMapping("/events/notify")
    NotifyEvent.CopyReply handleNotifyEvent(@RequestBody NotifyEvent event) {
        return notifyEventService.handleNotifyEvent(event);
    }
}
