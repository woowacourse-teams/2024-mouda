import type ButtonProps from './Button.type';
import { shapes } from './Button.style';

export default function Button(props: ButtonProps) {
  const { shape, onClick, disabled, children } = props;
  return (
    <button css={shapes(shape, disabled)} onClick={onClick} disabled={disabled}>
      {children}
    </button>
  );
}
