
export class RegexPattern {
    public static CPF_REGEX = /^(\d{3}[.-]?\d{3}[.-]?\d{3}[.-]?\d{2}|\d{11})$/;
    public static PHONE_REGEX = /^\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\)? ?(?:[2-8]|9[0-9])[0-9]{3}\-?[0-9]{4}$/;
    public static EMAIL_REGEX = /[a-zA-Z0-9\+\.\\_\\%\-\+]{1,256}\@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+/;
}
