import { useTheme } from '@emotion/react';
import * as S from './FunnelTextArea.style';
import { forwardRef } from 'react';

interface FunnelTextAreaProps
  extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {}

export default forwardRef(function FunnelTextArea(props: FunnelTextAreaProps) {
  const { ...rest } = props;

  const theme = useTheme();

  return <textarea css={S.textArea({ theme })} {...rest} />;
});
