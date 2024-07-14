package br.com.uanderson.api_rest.controllers.v1;

import br.com.uanderson.api_rest.model.dtos.OrderResultDTO;
import br.com.uanderson.api_rest.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("orders_v1")
@RequestMapping("v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders API. Version 1", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/filter")
    @Operation(
            summary = "Get orders by filter",
            parameters = {
                    @Parameter(name = "filter", description =
                            """
                                    <strong>Filter</strong>:
                                    <ol>
                                        <li>By order number</li>
                                        <li>By order date</li>
                                        <li>By delivery date</li>
                                        <li>By status</li>
                                    </ol>
                                    </ol>
                                    """, required = true),
                    @Parameter(name = "order_number", description =
                            """
                                    <b>Order number</b>
                                    <p>Number of the order to filter</p>
                                    <ul>
                                        <li>Format: valid order number</li>
                                    </ul>
                                    Filter 1.
                                    """
                    ),
                    @Parameter(name = "since", description =
                            """
                                    <b>Since date</b>
                                    <p>Use with order date and delivery date filters</p>
                                    <ul>
                                        <li>Format: yyyy-MM-dd HH:mm:ss</li>
                                    </ul>
                                    Filter 2 and 3.
                                                """
                    ),
                    @Parameter(name = "until", description =
                            """
                                    <b>Until date</b>
                                    <p>Use with order date and delivery date filters</p>
                                    <ul>
                                        <li>Format: yyyy-MM-dd HH:mm:ss</li>
                                    </ul>
                                    Filter 2 and 3.
                                                """
                    ),
                    @Parameter(name = "status", description =
                            """
                                    <b>Order status</b>
                                    <p>Possible values:</p>
                                    <ul>
                                        <li>DELIVERED</li>
                                        <li>PENDING</li>
                                        <li>CANCELED</li>
                                    </ul>
                                    Filter 4.
                                                """
                    ),
                    @Parameter(name = "customer_email", description =
                            """
                                    <b>Customer email</b>
                                    <p>Requires for all filters. Orders listed will be related to this email</p>
                                    <ul>
                                        <li>Format: valid email</li>
                                    </ul>
                                                """
                            , required = true),
                    @Parameter(name = "page", description = "Page number default 0"),
                    @Parameter(name = "size", description = "Page size default 10")
            },
            description = "Get orders by filter",
            responses = {
                    @ApiResponse(responseCode = "500", description = "Internal server erro"),
                    @ApiResponse(responseCode = "200", description = "Request successful")
            },
            security = {@SecurityRequirement(name = "security_auth")}
    )
    public ResponseEntity<OrderResultDTO> getOrdersByFilter(
            @RequestParam(name = "filter", defaultValue = "0") int filter,
            @RequestParam(name = "order_number", required = false) String orderNumber,
            @RequestParam(name = "since", required = false) String since,
            @RequestParam(name = "until", required = false) String until,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "customer_email", required = false) String customerEmail,
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    ) {

        try {
            return ResponseEntity.ok(orderService.getOrdersByFilter(
                    filter, orderNumber, since, until, status, customerEmail,
                    pageNumber, pageSize)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }


}//class
