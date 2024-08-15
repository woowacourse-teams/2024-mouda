import Button from '@_components/Button/Button';
import { PropsWithChildren } from 'react';

interface FunnelButtonProps {
  onClick: () => void;
}

export default function FunnelButton(
  props: PropsWithChildren<FunnelButtonProps>,
) {
  const { children, onClick } = props;

  return (
    <Button shape="bar" onClick={onClick}>
      {children}
    </Button>
  );
}
