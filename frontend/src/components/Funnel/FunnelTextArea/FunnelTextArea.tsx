import { useTheme } from '@emotion/react';
import * as S from './FunnelTextArea.style';
import { forwardRef, Ref, TextareaHTMLAttributes } from 'react';

interface FunnelTextAreaProps
  extends TextareaHTMLAttributes<HTMLTextAreaElement> {}

export default forwardRef(function FunnelTextArea(
  props: FunnelTextAreaProps,
  ref?: Ref<HTMLTextAreaElement>,
) {
  const { ...rest } = props;

  const theme = useTheme();

  return <textarea ref={ref} css={S.textArea({ theme })} {...rest} />;
});
