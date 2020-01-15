import { ILawyer } from './lawyer.model';

export interface IBooking {
  id?: number;
  bookingId?: string;
  bookingName?: string;
  paymentApproved?: boolean;
  lawyerIdId?: ILawyer;
}

export const defaultValue: Readonly<IBooking> = {
  paymentApproved: false
};
