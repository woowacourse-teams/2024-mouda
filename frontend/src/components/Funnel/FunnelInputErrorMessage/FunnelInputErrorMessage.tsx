import { useTheme } from '@emotion/react';
import { HTMLAttributes, PropsWithChildren } from 'react';

interface FunnelInputErrorMessageProps
  extends HTMLAttributes<HTMLSpanElement> {}

export default function FunnelInputErrorMessage(
  props: PropsWithChildren<FunnelInputErrorMessageProps>,
) {
  const { children, ...rest } = props;

  const theme = useTheme();

  return (
    <span css={[theme.typography.b3]} {...rest}>
      {children}
    </span>
  );
}
