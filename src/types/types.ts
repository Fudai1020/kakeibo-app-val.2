export type PaymentResponse ={
    id: number;
    name?: string;
    memo?: string;
    paymentDate: string;
    isPrivate: boolean;
    amount: number;
    paymentCategoryId: number;
    paymentCategoryName: string;
    userId: number;
}