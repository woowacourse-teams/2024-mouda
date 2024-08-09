import * as S from '@_components/KebabMenu/KebabMenu.style';
import { useRef, useState, FocusEvent } from 'react';
import KebabButton from '@_common/assets/kebab_menu.svg';
import { useTheme } from '@emotion/react';

type Option = { name: string; onClick: () => void };
export interface KebabMenuProps {
  options: Option[];
}

export default function KebabMenu(props: KebabMenuProps) {
  const theme = useTheme();
  const [isKebabOpen, setIsKebabOpen] = useState(false);
  const { options } = props;

  const handleKebabToggle = () => {
    setIsKebabOpen((prevValue) => !prevValue);
  };

  const optionsRef = useRef<HTMLDivElement>(null);

  const handleKebabClose = (e: FocusEvent<HTMLButtonElement>) => {
    if (
      optionsRef.current &&
      !optionsRef.current.contains(e.relatedTarget as Node)
    ) {
      setIsKebabOpen(false);
    }
  };

  const handleOptionClick = (onClick: () => void) => {
    setIsKebabOpen(false);
    onClick();
  };

  return (
    <div css={S.kebabContainer({ theme })}>
      <button onClick={handleKebabToggle} onBlur={handleKebabClose}>
        <KebabButton />
      </button>
      {isKebabOpen && (
        <div ref={optionsRef} css={S.optionBox({ theme })}>
          {options.map((option) => {
            return (
              <button
                key={option.name}
                onClick={() => handleOptionClick(option.onClick)}
              >
                {option.name}
              </button>
            );
          })}
        </div>
      )}
    </div>
  );
}
