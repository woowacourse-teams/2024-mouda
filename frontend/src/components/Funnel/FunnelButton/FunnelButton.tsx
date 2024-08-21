import Button from '@_components/Button/Button';
import { PropsWithChildren } from 'react';

interface FunnelButtonProps {
  disabled?: boolean;
  onClick: () => void;
}

export default function FunnelButton(
  props: PropsWithChildren<FunnelButtonProps>,
) {
  const { children, disabled, onClick } = props;

  return (
    <Button shape="bar" onClick={onClick} disabled={disabled}>
      {children}
    </Button>
  );
}
