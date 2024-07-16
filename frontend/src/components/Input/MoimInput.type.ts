import { ChangeEvent } from 'react';
import { InputInfoType } from '../../constants';

export default interface MoimInputProps {
  name: string;
  data: InputInfoType;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}
