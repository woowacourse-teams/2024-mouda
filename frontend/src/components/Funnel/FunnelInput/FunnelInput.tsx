import { InputHTMLAttributes } from 'react';
import * as S from './FunnelInput.style';
import { useTheme } from '@emotion/react';

interface FunnelInputProps extends InputHTMLAttributes<HTMLInputElement> {}

export default function FunnelInput(props: FunnelInputProps) {
  const { ...rest } = props;

  const theme = useTheme();

  return <input css={S.input({ theme })} {...rest} />;
}
