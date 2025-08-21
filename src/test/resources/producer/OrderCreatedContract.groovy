package producer

org.springframework.cloud.contract.spec.Contract.make {
    label 'order_created_event'
    description 'Orders service emits ORDER_CREATED on creation'
    input { triggeredBy('triggerOrderCreated()') }
    outputMessage {
        sentTo('orders.events')
        headers { contentType(applicationJson()) }
        body([
                type: 'ORDER_CREATED',
                orderId: $(consumer(regex(uuid())), producer(uuid())),
                occurredAt: $(consumer(regex(iso8601WithOffset())), producer(regex(iso8601WithOffset()))),
                payload: [
                        email: $(consumer(nonEmpty())),
                        amount: $(consumer(anyNumber()))
                ]
        ])
    }
}
