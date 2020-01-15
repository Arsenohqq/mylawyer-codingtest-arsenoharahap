export interface ILawyer {
  id?: number;
  lawyerId?: string;
  lawyerFullName?: string;
  lawyerPhoneNumber?: string;
}

export const defaultValue: Readonly<ILawyer> = {};
