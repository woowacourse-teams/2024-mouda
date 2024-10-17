import useEditMyInfo from '@_hooks/mutaions/useEditMyInfo';
import useMyInfo from '@_hooks/queries/useMyInfo';
import { useState, useRef, useEffect, ChangeEvent } from 'react';
import { validateNickName } from '../validate';

export default function useMyPage() {
  const { myInfo } = useMyInfo();
  const { mutate } = useEditMyInfo();
  const fileInput = useRef<HTMLInputElement | null>(null);
  const [profile, setProfile] = useState(myInfo?.profile || '');
  const [nickname, setNickname] = useState(myInfo?.nickname || '');
  const [description, setDescription] = useState(myInfo?.description || '');
  const [selectedFile, setSelectedFile] = useState<File | string>('');
  const [isEditing, setIsEditing] = useState(false);
  const [isReset, setIsReset] = useState('false');
  const [isShownRest, setIsShownRest] = useState(false);

  useEffect(() => {
    if (myInfo) {
      setNickname(myInfo.nickname || '');
      setDescription(myInfo.description || '');
      setProfile(myInfo.profile || '');
      setSelectedFile(myInfo.profile || '');
      myInfo.profile && setIsShownRest(true);
    }
  }, [myInfo]);

  const handleEditClick = () => {
    setIsEditing((prev) => !prev);
    setSelectedFile('');
  };

  const onChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]); // 선택한 파일을 상태에 저장
      setIsShownRest(true);
      setIsReset('false');
    } else {
      setProfile(myInfo?.profile ?? '');
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      if (reader.readyState === 2 && typeof reader.result === 'string') {
        setProfile(reader.result);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const onUpload = async () => {
    const formData = new FormData();

    // 파일 추가
    formData.append('file', selectedFile);

    // 문자열 데이터 추가
    formData.append('nickname', nickname ?? '');
    formData.append('description', description ?? '');
    formData.append('isReset', isReset);

    try {
      // 서버로 파일 및 데이터 전송
      mutate(formData); // FormData 객체 자체를 전달
      handleEditClick(); // 편집 모드 비활성화
    } catch (error) {
      console.error('파일 업로드 실패', error);
    }
  };

  const handleProfileClick = () => {
    fileInput.current?.click(); // ProfileFrame 클릭 시 파일 선택창 열기
  };

  const handleCancel = () => {
    // 편집 취소시 기존 데이터 복구
    setProfile(myInfo?.profile || '');
    setNickname(myInfo?.nickname || '');
    setDescription(myInfo?.description || '');
    setIsEditing(false);
    setIsReset('false');
    myInfo?.profile && setIsShownRest(false);
  };

  const handleDefaultProfile = () => {
    setProfile('');
    setSelectedFile('');
    setIsReset('true');
    setIsShownRest(false);
  };

  const isValidMyInfo = validateNickName(nickname);

  return {
    fileInput,
    myInfo,
    profile,
    nickname,
    description,
    selectedFile,
    isEditing,
    isReset,
    isShownRest,
    isValidMyInfo,
    setNickname,
    setDescription,
    setIsEditing,
    handleEditClick,
    onChange,
    onUpload,
    handleProfileClick,
    handleCancel,
    handleDefaultProfile,
  };
}
