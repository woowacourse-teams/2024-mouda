import * as S from '@_components/KebabMenu/KebabMenu.style';
import { FocusEvent, useRef, useState } from 'react';
import KebabButton from '@_common/assets/kebab_menu.svg';
import { useTheme } from '@emotion/react';
import { createPortal } from 'react-dom'; // createPortal 추가

type Option = { name: string; disabled: boolean; onClick: () => void };
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

  const kebabMenu = isKebabOpen ? (
    <div ref={optionsRef} css={S.optionBox({ theme })}>
      {options.map((option) => (
        <button
          css={S.kebabItem({ theme })}
          aria-label={option.name}
          key={option.name}
          onClick={() => handleOptionClick(option.onClick)}
          disabled={option.disabled}
        >
          {option.name}
        </button>
      ))}
    </div>
  ) : null;

  return (
    <div css={S.kebabContainer({ theme })}>
      <button
        onClick={handleKebabToggle}
        onBlur={handleKebabClose}
        aria-label="케밥 메뉴 활성화 버튼"
        aria-checked={isKebabOpen}
      >
        <KebabButton />
      </button>
      {createPortal(kebabMenu, document.body)} {/* createPortal 사용 */}
    </div>
  );
}
