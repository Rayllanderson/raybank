import { Container } from "@/components/Container"
import InvoiceCard from "@/components/InvoiceCard"
import InvoiceCardLoading from "@/components/loading/InvoiceCardLoading"

export default function loading() {
    return (
        <Container >
            <h1 className="font-semibold text-xl mb-1">Resumo de faturas</h1>

            <div>
                {
                    [1, 2, 3, 4, 5].map(invoice => {
                        return <InvoiceCardLoading key={invoice} />
                    })
                }
            </div>
        </Container>
    )
}
