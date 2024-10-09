import * as S from './MyInfoTabBar.style';

import { common } from '@_common/common.style';
import { useTheme } from '@emotion/react';
import { Fragment, useState } from 'react';

export type MyInfoTab = '정보';

const tabs: MyInfoTab[] = ['정보'];
interface MyIfoTabBarProps {
  description?: string;
  isEditing: boolean;
  setDescription: (description: string) => void;
}

export default function MyInfoTabBar(props: MyIfoTabBarProps) {
  const [currentTab, setCurrenttab] = useState<MyInfoTab>('정보');
  const { description, isEditing, setDescription } = props;
  const theme = useTheme();

  const handleDescriptionChange = (
    e: React.ChangeEvent<HTMLTextAreaElement>,
  ) => {
    setDescription(e.target.value); // 입력된 값을 부모 컴포넌트로 전달
  };

  return (
    <Fragment>
      <nav css={S.tabStyle({ theme })}>
        {tabs.map((tab, index) => (
          <p
            key={index}
            css={[
              S.tabItemStyle({ theme, isTurnedOn: currentTab === tab }),
              common.nonDrag,
            ]}
            onClick={() => setCurrenttab(tab)}
          >
            {tab}
          </p>
        ))}
      </nav>
      {currentTab === '정보' && (
        <section css={{ width: '97%', minHeight: '40vh' }}>
          <h3 css={theme.typography.s2}>소개</h3>
          {isEditing ? (
            <textarea
              value={description}
              onChange={handleDescriptionChange} // 텍스트 수정 핸들러 연결
              css={{
                width: '100%',
                minHeight: '100px',
                ...theme.typography.b4,
              }}
            />
          ) : (
            <p css={theme.typography.b4}>{description}</p>
          )}
        </section>
      )}
    </Fragment>
  );
}
