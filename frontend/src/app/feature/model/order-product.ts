export class OrderProduct{
    skuCode!: string;
    price!: number;
    quantity!: number;
    userDetails!: UserDetails;
}

export class UserDetails{
    email?: string;
    firstName?: string;
    lastName?: string
}