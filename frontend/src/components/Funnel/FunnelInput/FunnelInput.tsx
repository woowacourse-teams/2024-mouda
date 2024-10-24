import { forwardRef, InputHTMLAttributes, Ref } from 'react';
import * as S from './FunnelInput.style';
import { useTheme } from '@emotion/react';

interface FunnelInputProps extends InputHTMLAttributes<HTMLInputElement> {}

export default forwardRef(function FunnelInput(
  props: FunnelInputProps,
  ref?: Ref<HTMLInputElement>,
) {
  const { ...rest } = props;

  const theme = useTheme();

  return <input ref={ref} css={S.input({ theme })} {...rest} />;
});
